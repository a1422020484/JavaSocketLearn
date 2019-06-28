package actionExecutorPackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionExecutor<T extends ActionTask> {

	int id;
	AtomicInteger counter;
	ExecutorService executorService;

	ActionExecutor(int id){
		this.id = id;
		counter = new AtomicInteger();
		executorService = Executors.newSingleThreadExecutor(new DefaultThreadFactory(id));
	}
	
	void execute(final T t) {
		executorService.execute(t);
	}
	
	void stop() { 
		executorService.shutdown();
	}
}
