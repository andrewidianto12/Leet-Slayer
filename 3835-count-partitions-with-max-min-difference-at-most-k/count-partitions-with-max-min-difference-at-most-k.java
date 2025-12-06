class Solution {
    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        long mod = 1_000_000_007L;

        long[] dp = new long[n + 1];
        long[] prefix = new long[n + 1];

        dp[0] = 1;         
        prefix[0] = 1;

        java.util.Deque<Integer> minQ = new java.util.ArrayDeque<>();
        java.util.Deque<Integer> maxQ = new java.util.ArrayDeque<>();

        int left = 0;

        for (int right = 0; right < n; right++) {

            while (!minQ.isEmpty() && nums[minQ.peekLast()] > nums[right])
                minQ.pollLast();
            minQ.addLast(right);

            while (!maxQ.isEmpty() && nums[maxQ.peekLast()] < nums[right])
                maxQ.pollLast();
            maxQ.addLast(right);

            while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > k) {
                if (minQ.peekFirst() == left) minQ.pollFirst();
                if (maxQ.peekFirst() == left) maxQ.pollFirst();
                left++;
            }

            long total = (prefix[right] - (left == 0 ? 0 : prefix[left - 1])) % mod;
            if (total < 0) total += mod;

            dp[right + 1] = total;
            prefix[right + 1] = (prefix[right] + dp[right + 1]) % mod;
        }

        return (int) dp[n];
    }
}
