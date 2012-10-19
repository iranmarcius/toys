package toys.tests;

import toys.application.runnables.ToyRunnable;

public class TestThread extends ToyRunnable {

	@Override
	protected void execute() {
		System.out.println("Thread: " + System.currentTimeMillis());
		
	}
}
