import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;

class Player {

    private static final int NOT_SET = -1;
    private static final int HUMAN_LIMIT = 100;
    private static final int ZOMBIE_LIMIT = 100;

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

                Move move = player.move(start);
                long delta = new Date().getTime() - start;
                System.out.println("" + move.x + " " + move.y + " " + move.message);
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

    public Move move(long start) {

        if (zombieCount == 1) {
            return new Move(zombieXNext[0], zombieYNext[0], "Zombie");
        }

        if (humanCount == 1) {
            return new Move(humanX[0], humanY[0], "Human");
        }

        final int dangerIndex = mostDangerousZombie();
        if (dangerIndex != NOT_SET) {
            return new Move(zombieXNext[dangerIndex], zombieYNext[dangerIndex], "Z " + zombieId[dangerIndex]);
        }

        int targetX = 0;
        int targetY = 0;
        for (int i = 0; i < humanCount; i++) {
            targetX += humanX[i];
            targetY += humanY[i];
        }
        targetX = targetX / humanCount;
        targetY = targetY / humanCount;

        return new Move(targetX, targetY, "default");

    }

    private long dist(int x0, int y0, int x1, int y1) {
        return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1);
    }

    private int mostDangerousZombie() {
        int zPretender = NOT_SET;
        long zDistPretender = Long.MAX_VALUE;
        for (int z = 0; z < zombieCount; z++) {

            long distPretender = Long.MAX_VALUE;
            for (int h = 0; h < humanCount; h++) {
                final long dist = dist(zombieX[z], zombieY[z], humanX[z], humanY[z]);
                if (dist < distPretender) {
                    distPretender = dist;
                }
            }

            if (distPretender < zDistPretender) {
                zDistPretender = distPretender;
                zPretender = z;
            }

        }
        return zPretender;
    }

    static class Move {
        private final int x;
        private final int y;
        private final String message;

        Move(int x, int y, String message) {
            this.x = x;
            this.y = y;
            this.message = message;
        }
    }


}

