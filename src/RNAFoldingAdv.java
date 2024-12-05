public class RNAFoldingAdv {
    private final int MIN_DISTANCE = 2; // Minimum distance for valid pairing
    private final int MATCH_SCORE = 2; // A-U, G-C
    private final int GU_SCORE = 1;    // G-U
    private final int STACKED_SCORE = 1; // Bonus for stacked pairs

    // Function to determine pairing score
    private int getPairScore(char a, char b) {
        if ((a == 'A' && b == 'U') || (a == 'U' && b == 'A')) return MATCH_SCORE;
        if ((a == 'G' && b == 'C') || (a == 'C' && b == 'G')) return MATCH_SCORE;
        if ((a == 'G' && b == 'U') || (a == 'U' && b == 'G')) return GU_SCORE;
        return 0;
    }

    public String foldRNA(String sequence) {
        int n = sequence.length();
        int[][] dp = new int[n][n];
        int[][] traceback = new int[n][n];

        // Fill the DP table
        for (int length = MIN_DISTANCE; length < n; length++) {
            for (int i = 0; i + length < n; i++) {
                int j = i + length;
                dp[i][j] = dp[i][j - 1]; // Skip pairing j
                traceback[i][j] = -1;   // No pairing

                // Try pairing j with any k < j
                for (int k = i; k < j - MIN_DISTANCE + 1; k++) {
                    int basePairScore = getPairScore(sequence.charAt(k), sequence.charAt(j));
                    int score = (k > i ? dp[i][k - 1] : 0)
                            + (k + 1 < j ? dp[k + 1][j - 1] : 0)
                            + basePairScore;

                    // Add stacked base pair bonus
                    if (k > i && j - 1 > k) {
                        score += getPairScore(sequence.charAt(k - 1), sequence.charAt(j - 1)) == 0
                                ? 0 : STACKED_SCORE;
                    }

                    if (score > dp[i][j]) {
                        dp[i][j] = score;
                        traceback[i][j] = k;
                    }
                }
            }
        }

        // Reconstruct the solution
        return reconstruct(traceback, sequence, 0, n - 1);
    }

    private String reconstruct(int[][] traceback, String sequence, int i, int j) {
        if (i >= j) return ".".repeat(Math.max(0, j - i + 1));
        if (traceback[i][j] == -1) return reconstruct(traceback, sequence, i, j - 1) + ".";
        int k = traceback[i][j];
        return reconstruct(traceback, sequence, i, k - 1) + "(" + reconstruct(traceback, sequence, k + 1, j - 1) + ")";
    }
}

