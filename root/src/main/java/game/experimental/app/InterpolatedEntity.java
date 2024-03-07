package game.experimental.app;

import game.experimental.engine.*;
import game.experimental.utils.Vector2F;

public class InterpolatedEntity {

    private interface InterpolationStrategy<T> {
        T interpolate(T start, T end, float factor);
    }
    
    private class InterpolatedVariable<T> {
        private T newValue;
        private T lastValue;

        public InterpolatedVariable(T value) {
            this.newValue = value;
            this.lastValue = value;
        }

        public void set(T value) {
            this.lastValue = this.newValue;
            this.newValue = value;
        }

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

    public InterpolatedEntity(Entity entity) {
        this.entity = entity;
        this.lastUpdateTick = entity.getBeginTick();

        this.position = new InterpolatedVariable<Vector2F>(entity.getPosition());
        this.angle = new InterpolatedVariable<Float>(entity.getAngle());
    }

    public void interpolate(long tickCount) {
        if (tickCount > this.lastUpdateTick) {
            this.lastUpdateTick = tickCount;

            this.position.set(entity.getPosition());
            this.angle.set(entity.getAngle());
        }
    }

    public Vector2F getPosition(float factor) {
        return position.get(factor, vecLinearInterpolator);
    }

    public float getAngle(float factor) {
        return angle.get(factor, floatLinearInterpolator);
    }

    public Entity getEntity() {
        return this.entity;
    }

}
