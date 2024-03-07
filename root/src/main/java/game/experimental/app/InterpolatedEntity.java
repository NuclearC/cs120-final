package game.experimental.app;

import game.experimental.engine.*;
import game.experimental.utils.Vector2F;

/**
 * Represents a wrapper around Entity, which has 
 * certain properties as position and angle interpolated. 
 */
public class InterpolatedEntity {

    /**
     * Must be specialized to all the types of properties that 
     * will be interpolated. 
     */
    private interface InterpolationStrategy<T> {
        T interpolate(T start, T end, float factor);
    }
    
    /**
     * Represents a variable that will be interpolated. 
     */
    private class InterpolatedVariable<T> {
        private T newValue;
        private T lastValue;

        /**
         * Create an interpolated variable. 
         * @param value the current value of variable. 
         */
        public InterpolatedVariable(T value) {
            this.newValue = value;
            this.lastValue = value;
        }

        /**
         * Sets a new starting point for interpolation. 
         * Now the initial point will be equal to the given value. 
         * @param value the value of initial point. 
         */
        public void set(T value) {
            this.lastValue = this.newValue;
            this.newValue = value;
        }

        /** 
         * Get an interpolated value by the given factor and strategy. 
         * @param factor must be within 0 and 1
         * @param strategy an implementation of InterpolationStrategy for the given variable type. 
         */
        public T get(float factor, InterpolationStrategy<T> strategy) {
            if (factor < 0.f) factor = 0.f;
            if (factor > 1.f) factor = 1.f;
            return strategy.interpolate(lastValue, newValue, factor);
        }
    }
    
    private Entity entity;

    private long lastUpdateTick;

    private InterpolatedVariable<Vector2F> position;
    private InterpolatedVariable<Float> angle;

    private static final InterpolationStrategy<Vector2F> vecLinearInterpolator = new InterpolationStrategy<Vector2F>() {
        @Override 
        public Vector2F interpolate(Vector2F start, Vector2F end, float factor) {
            return start.add(end.subtract(start).multiply(factor));
        }
    };
    
    private static final InterpolationStrategy<Float> floatLinearInterpolator = new InterpolationStrategy<Float>() {
        @Override 
        public Float interpolate(Float start, Float end, float factor) {
            return start + (end - start) * factor;
        }
    };

    private static final InterpolationStrategy<Float> floatAngleLinearInterpolator = new InterpolationStrategy<Float>() {
        @Override 
        public Float interpolate(Float start, Float end, float factor) {
            return start + (end - start) * factor;
        }
    };

    /**
     * Create an interpolated entity wrapper around the given entity. 
     * @param entity the given entity
     */
    public InterpolatedEntity(Entity entity) {
        this.entity = entity;
        this.lastUpdateTick = entity.getBeginTick();

        this.position = new InterpolatedVariable<Vector2F>(entity.getPosition());
        this.angle = new InterpolatedVariable<Float>(entity.getAngle());
    }

    /**
     * Essentially just updates the interpolated values
     * whether the engine has passed a frame. 
     * @param tickCount the tickCount of the parent Room of the given Entity
     */
    public void interpolate(long tickCount) {
        if (tickCount > this.lastUpdateTick) {
            this.lastUpdateTick = tickCount;

            this.position.set(entity.getPosition());
            this.angle.set(entity.getAngle());
        }
    }

    /**
     * Gets the position interpolated by the specified factor
     * @param factor the given factor (must be between 0 and 1)
     * @return the interpolated position
     */
    public Vector2F getPosition(float factor) {
        return position.get(factor, vecLinearInterpolator);
    }

    /**
     * Gets the angle of the entity interpolated by the specified factor
     * @param factor the given factor (must be between 0 and 1)
     * @return the interpolated angle
     */
    public float getAngle(float factor) {
        return angle.get(factor, floatAngleLinearInterpolator);
    }

    /**
     * Gets the owned entity
     */
    public Entity getEntity() {
        return this.entity;
    }

}
