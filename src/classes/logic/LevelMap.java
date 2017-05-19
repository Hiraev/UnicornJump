package logic;

import logic.barriers.Barrier;
import logic.bonuses.Bonus;
import logic.platforms.Platform;

import java.util.List;

public class LevelMap {
    private List<Platform> platforms;               //Платформы
    private List<Barrier> barriers;                 //Препятствия
    private List<Bonus> bonuses;                    //Бонусы

    protected LevelMap(List<Platform> platforms,
                       List<Barrier> barriers,
                       List<Bonus> bonuses) {
        this.platforms = platforms;
        this.barriers = barriers;
        this.bonuses = bonuses;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

}