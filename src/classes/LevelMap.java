import barriers.Barrier;
import bonuses.Bonus;
import platforms.*;

import java.util.ArrayList;
import java.util.List;

public class LevelMap {
    private List<Platform> platforms;               //Платформы
    private List<Barrier> barriers;                 //Препятствия
    private List<Bonus> bonuses;                    //Бонусы

    /**
     * @param level  уровень
     * @param height кол-во платформ
     * @param width  ширина окна
     */
    public LevelMap(int level, int height, int width) {
        platforms = new ArrayList<>();
        barriers = new ArrayList<>();
        bonuses = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            double random = Math.random();
            double random2 = Math.random();
            double probability = Math.abs(random *(double) level);
            if (probability > 0.3){
                if (random2 > 0.2) platforms.add(new BasicPlatform());
                else platforms.add(new MovePlatform(width));
            } else if (probability > 0.1) {
                if (random2 > 0.2) platforms.add(new MovePlatform(width));
                else platforms.add(new VanishPlatform(width));
            } else if (probability > 0.05) {
                if (random2 > 0.2) platforms.add(new VanishPlatform(width));
                else platforms.add(new BasicFadePlatform());
            } else if (probability > 0.01) {
                if (random2 > 0.2) platforms.add(new BasicFadePlatform());
                else platforms.add(new MoveFadePlatform(width));
            } else {
                if (random2 > 0.4) platforms.add(new MoveFadePlatform(width));
                else if (random2 > 0.2) platforms.add(new BasicFadePlatform());
                else if (random2 > 0.08) platforms.add(new MovePlatform(width));
                else  platforms.add(new BasicPlatform());
            }

            //Сделать барьеры и бонусы!
        }
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<Bonus> getBonuses() {
        return bonuses;
    }
}