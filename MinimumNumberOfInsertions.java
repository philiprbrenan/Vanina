/*------------------------------------------------------------------------------
Minimum number of extra parentheses required to make a string of ( and ) satisfy
the grammar: S -> (S)) | empty.

Philip R Brenan at appaapps dot com, Appa Apps Ltd Inc., 2024
------------------------------------------------------------------------------*/
class MinimumNumberOfInsertions                                                 // Dynamic programming heuristic search for smallest number of insertions
 {final private static boolean debug = false;                                   // Print debugging information if true
  final static java.util.Map<String,Integer> map = new java.util.TreeMap<>();   // Cache earlier results to obtain a dynamic programming solution

  private static class Reduce                                                   // Reduce a string
   {final private String
      source,                                                                   // Source string
      match,                                                                    // String to be removed
      target;                                                                   // Reduced string
    final private int     insertions;                                           // Number of insertions required by this reduction
    final private boolean reduced;                                              // Whether a reduction took place or not

    static private String reduce                                                // Remove the first instance of a specified sub-string from a string
     (final String Source, final String Match)                                  // Source string, string to match and remove
     {final int s = Source.indexOf(Match), m = Match.length();                  // Look for the match string
      return s >= 0 ? Source.substring(0, s) + Source.substring(s+m) : Source;  // Position of match string
     }

    public String toString()                                                    // Print details of a reduction
     {return reduced  ?  source+"-"+match+"="+target+"+"+insertions : source;
     }

    Reduce                                                                      // Reduce a string by removing a matching string within it
     (final String Source, final String Match, final int Insertions)            // Source string, string to match and remove
     {target     = reduce(source = Source, match = Match);                      // The effect of the reduction
      reduced    = !target.equals(source);                                      // Whether a reduction occurred or not
      insertions = Insertions;                                                  // Number of insertions required for reduction
     }
   }

  private static Integer checkOneReduction                                      // Dynamically remove a possible sub-string accounting for the number of parenthesis inserted
   (final String String, final String Match, final int Inserts)                 // String to reduce, reduce by this string, number of insertions in this reduction
   {final Reduce r = new Reduce(String, Match, Inserts);                        // Reduce
    if (debug) say(r);                                                          // Debug rule
    return r.reduced ? Inserts + insertions(r.target) : null;                   // Reduce by rule and continue or give up
   }

  public static int insertions                                                  // Number of insertions required to correct source string
   (final String Source)                                                        // Source string to check
   {if (debug) say("Insertions", Source);
    if (map.containsKey(Source)) return map.get(Source);                        // Look up cached value as this method is idempotent
    if (Source.length() == 0) return 0;                                         // No more reductions possible

    final Integer[] h =                                                         // The heuristics
      {checkOneReduction(Source, "())", 0),
       checkOneReduction(Source, "()",  1),
       checkOneReduction(Source, ")(",  4),
       checkOneReduction(Source, "))",  1),
       checkOneReduction(Source, "(",   2),
       checkOneReduction(Source, ")",   2),
      };

    Integer m = null;                                                           // Smallest number of insertions indicates best path
    for(Integer c : h) if (c != null && (m == null || m > c)) m = c;            // Only consider reductions that exhausted the input string
    assert m != null;                                                           // We consider (, ), and the empty string so at least one reduction will succeed

    map.put(Source, m);                                                         // Cache shortest replacement sequence for this string
    return m;
   }

  private static void say(Object...O)                                           // Say something on Stderr
   {final StringBuilder b = new StringBuilder();
    for(Object o: O) {b.append(" "); b.append(o);}
    System.err.println((O.length > 0 ? b.substring(1) : ""));
   }

  public static void main(String[] args)                                        // Test some strings against the expected results
   {assert new Reduce("(()))", "())", 0).reduced;                               // Test one reduction
    assert new Reduce("(()))", "())", 0).target.equals("()");

    assert new Reduce(")", ")", 2).reduced;
    assert new Reduce(")", ")", 2).target.equals("");

    int tests = 0;
    assert insertions("")      == 0; ++tests;                                   // Test full reductions to an empty string to get the proposed minimum number of insertions
    assert insertions(")")     == 2; ++tests;
    assert insertions("))")    == 1; ++tests;
    assert insertions("())")   == 0; ++tests;
    assert insertions("(())")  == 2; ++tests;
    assert insertions("))((")  == 5; ++tests;
    assert insertions(")))((") == 7; ++tests;
    say("Passed", tests, "tests.");
   }
 }
