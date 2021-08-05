import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;

class Player {

    public static final int HUMAN_LIMIT = 100;
    public static final int ZOMBIE_LIMIT = 100;

    private final int[] humanId = new int[HUMAN_LIMIT];
    private final int[] humanX = new int[HUMAN_LIMIT];
    private final int[] humanY = new int[HUMAN_LIMIT];

    private final int[] zombieId = new int[ZOMBIE_LIMIT];
    private final int[] zombieX = new int[ZOMBIE_LIMIT];
    private final int[] zombieY = new int[ZOMBIE_LIMIT];
    private final int[] zombieXNext = new int[ZOMBIE_LIMIT];
    private final int[] zombieYNext = new int[ZOMBIE_LIMIT];

    private int meX;
    private int meY;
    private int humanCount;
    private int zombieCount;

    public static void main(String[] args) throws IOException {

        final Player player = new Player();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            long tick = 0;
            while (tick++ >= 0) {
                final StringTokenizer meTokenizer = new StringTokenizer(reader.readLine());
                final long start = new Date().getTime();
                final int x = Integer.parseInt(meTokenizer.nextToken());
                final int y = Integer.parseInt(meTokenizer.nextToken());
                player.setMe(tick, x, y);

                final int humanCount = Integer.parseInt(reader.readLine());
                player.setHumanCount(tick, humanCount);
                for (int i = 0; i < humanCount; i++) {
                    final StringTokenizer humanTokenizer = new StringTokenizer(reader.readLine());
                    final int humanId = Integer.parseInt(humanTokenizer.nextToken());
                    final int humanX = Integer.parseInt(humanTokenizer.nextToken());
                    final int humanY = Integer.parseInt(humanTokenizer.nextToken());
                    player.setHuman(tick, i, humanId, humanX, humanY);
                }

                final int zombieCount = Integer.parseInt(reader.readLine());
                player.setZombieCount(tick, zombieCount);
                for (int i = 0; i < zombieCount; i++) {
                    final StringTokenizer zombieTokenizer = new StringTokenizer(reader.readLine());
                    final int zombieId = Integer.parseInt(zombieTokenizer.nextToken());
                    final int zombieX = Integer.parseInt(zombieTokenizer.nextToken());
                    final int zombieY = Integer.parseInt(zombieTokenizer.nextToken());
                    final int zombieXNext = Integer.parseInt(zombieTokenizer.nextToken());
                    final int zombieYNext = Integer.parseInt(zombieTokenizer.nextToken());
                    player.setZombie(tick, i, zombieId, zombieX, zombieY, zombieXNext, zombieYNext);
                }

                int[] moves = player.move(start);
                long delta = new Date().getTime() - start;
                System.out.println("" + moves[0] + " " + moves[1] + " " + delta);
            }
        }
    }

    public void setMe(long tick, int x, int y) {
        meX = x;
        meY = y;
    }

    public void setHumanCount(long tick, int count) {
        humanCount = count;
    }

    public void setHuman(long tick, int index, int id, int x, int y) {
        humanId[index] = id;
        humanX[index] = x;
        humanY[index] = y;
    }

    public void setZombieCount(long tick, int count) {
        zombieCount = count;
    }

    public void setZombie(long tick, int index, int id, int x, int y, int nextX, int nextY) {
        zombieId[index] = id;
        zombieX[index] = x;
        zombieY[index] = y;
        zombieXNext[index] = nextX;
        zombieYNext[index] = nextY;
    }

    public int[] move(long start) {

        if (humanCount == 1) {
            return new int[]{humanX[0], humanY[0]};
        }

        if (zombieCount == 1) {
            return new int[]{zombieXNext[0], zombieYNext[0]};
        }

        int targetX = 0;
        int targetY = 0;
        for (int i = 0; i < humanCount; i++) {
            targetX += humanX[i];
            targetY += humanY[i];
        }
        targetX = targetX / humanCount;
        targetY = targetY / humanCount;

        return new int[]{targetX, targetY};

    }
}

