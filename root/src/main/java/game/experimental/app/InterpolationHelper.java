package game.experimental.app;

import game.experimental.engine.entities.*;

import java.util.*;

/**
 * A class to help keep entities interpolated. 
 */
public class InterpolationHelper {

    private Map<Entity, InterpolatedEntity> interpolatedEntities;

    public InterpolationHelper() {
        this.interpolatedEntities = new HashMap<>();
    }

    /**
     * Update the entity if its present in the interpolation table.
     * Otherwise add it to the table. 
     * Must be called every client frame. 
     * @param e the entity 
     * @param tickCount the tick count of the room. 
     */
    public void updateEntity(Entity e, long tickCount) {
        if (interpolatedEntities.containsKey(e)) {
            interpolatedEntities.get(e).updateFrame(tickCount);
        } else {
            interpolatedEntities.put(e, new InterpolatedEntity(e));
        }
    }

    /**
     * Retrieve the interpolated entity from the table. 
     * @param e the entity 
     * @return the interpolated entity or null
     */
    public InterpolatedEntity getInterpolatedEntity(Entity e) {
        if (interpolatedEntities.containsKey(e)) {
            return interpolatedEntities.get(e);
        } 
        return null;
    }
}
