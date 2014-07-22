package rawFoodCooker;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class Task extends ClientAccessor {

	private RawFoodCooker script;

	public Task(ClientContext ctx) {
		super(ctx);
		script = (RawFoodCooker)ctx.controller.script();
	}

	public RawFoodCooker script() {
		return script;
	}

	public abstract boolean activate();

	public abstract void execute();

}
