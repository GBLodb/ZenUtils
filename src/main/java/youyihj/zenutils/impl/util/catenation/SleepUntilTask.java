package youyihj.zenutils.impl.util.catenation;

import crafttweaker.api.world.IWorld;
import youyihj.zenutils.api.util.catenation.CatenationContext;
import youyihj.zenutils.api.util.catenation.ICatenationTask;
import youyihj.zenutils.api.util.catenation.IWorldCondition;

/**
 * @author youyihj
 */
public class SleepUntilTask implements ICatenationTask {
    private final IWorldCondition condition;
    private boolean sleep = true;

    public SleepUntilTask(IWorldCondition condition) {
        this.condition = condition;
    }

    @Override
    public void run(IWorld world, CatenationContext context) {
        if (sleep && condition.apply(world, context)) {
            sleep = false;
        }
    }

    @Override
    public boolean isComplete() {
        return !sleep;
    }
}
