package logic;

public interface LevelGenerator {
    LevelMap updateLevel();

    LevelMap getLevel();

    void levelDistributor();

    void resetLastPlatformY();

    int getLastPlatformY();
}