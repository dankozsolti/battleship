package battleship.results;

import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import java.util.List;

/**
 * DAO class for the {@link GameResults} entity.
 */
public class GameResultsDao extends GenericJpaDao<GameResults> {

    private static GameResultsDao instance;

    private GameResultsDao() {
        super(GameResults.class);
    }

    /**
     * Returns a game result instance.
     * @return Instance
     */
    public static GameResultsDao getInstance() {
        if (instance == null) {
            instance = new GameResultsDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("jpa-persistence-unit-1").createEntityManager());
        }
        return instance;
    }

    /**
     * Returns the list of {@code n} best results with respect to the time
     * spent for solving the puzzle.
     *
     * @param n the maximum number of results to be returned
     * @return the list of {@code n} best results with respect to the time
     * spent for solving the puzzle
     */
    public List<GameResults> findBest(int n) {
        return entityManager.createQuery("SELECT r FROM GameResults r WHERE r.solved = true ORDER BY r.duration ASC, r.created DESC", GameResults.class)
                .setMaxResults(n)
                .getResultList();
    }

}
