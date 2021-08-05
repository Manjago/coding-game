import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;

public class Player {

    public static final int HUMAN_LIMIT = 100;
    private final int[] humanX = new int[HUMAN_LIMIT];
    private final int[] humanY = new int[HUMAN_LIMIT];
    private int meX;
    private int meY;
    private int humanCount;

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
                    player.setHuman(tick, humanId, humanX, humanY);
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
                    player.setZombie(tick, zombieId, zombieX, zombieY, zombieXNext, zombieYNext);
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

    public void setHuman(long tick, int id, int x, int y) {
        humanX[id] = x;
        humanY[id] = y;
    }

    public void setZombieCount(long tick, int count) {
    }

    public void setZombie(long tick, int id, int x, int y, int nextX, int nextY) {
    }

    public int[] move(long start) {

        if (humanCount != 1) {
            return new int[]{meX, meY};
        }

        return new int[]{humanX[0], humanY[0]};
    }
}

