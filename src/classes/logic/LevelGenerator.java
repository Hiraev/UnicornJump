package logic;

public interface LevelGenerator {

    LevelMap getLevel();

    void updateLevel();

    void levelDistributor();

    void resetLastPlatformY();

    int getLastPlatformY();
}