package com.streams.assessment.functional;

import static com.streams.assessment.testutils.TestUtils.businessTestFile;
import static com.streams.assessment.testutils.TestUtils.currentTest;
import static com.streams.assessment.testutils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.task.assessment.TaskScheduler;

public class FunctionalTests {

	@Test
	void testCheckScheduler() throws IOException {
		TaskScheduler scheduler = new TaskScheduler();

		scheduler.addTask("Task1");
		scheduler.addTask("Task2");
		scheduler.addTask("Task3");
		scheduler.addTask("Task4");

		scheduler.addDependency("Task2", "Task1");
		scheduler.addDependency("Task3", "Task1");
		scheduler.addDependency("Task4", "Task2");
		scheduler.addDependency("Task4", "Task3");

		scheduler.scheduleTasks();

		String[] expectedOrder = { "Task2", "Task1", "Task4", "Task3" };
		String[] actualOrder = scheduler.getExecutedTasks().toArray(new String[0]);

		boolean orderIsCorrect = true;
		for (int i = 0; i < expectedOrder.length; i++) {
			if (!expectedOrder[i].equals(actualOrder[i])) {
				orderIsCorrect = false;
				break;
			}
		}

		yakshaAssert(currentTest(), orderIsCorrect ? "true" : "false", businessTestFile);
	}

	@Test
	void testChainOfDependencies() throws IOException {
		TaskScheduler scheduler = new TaskScheduler();
		scheduler.addTask("Task1");
		scheduler.addTask("Task2");
		scheduler.addTask("Task3");
		scheduler.addDependency("Task2", "Task1");
		scheduler.addDependency("Task3", "Task2");

		scheduler.scheduleTasks();

		String[] expectedOrder = { "Task2", "Task1", "Task3" };
		String[] actualOrder = scheduler.getExecutedTasks().toArray(new String[0]);

		boolean orderIsCorrect = expectedOrder.length == actualOrder.length && expectedOrder[0].equals(actualOrder[0])
				&& expectedOrder[1].equals(actualOrder[1]) && expectedOrder[2].equals(actualOrder[2]);
		yakshaAssert(currentTest(), orderIsCorrect ? "true" : "false", businessTestFile);
	}

	@Test
	void testMultipleIndependentTasks() throws IOException {
		TaskScheduler scheduler = new TaskScheduler();
		scheduler.addTask("Task1");
		scheduler.addTask("Task2");
		scheduler.addTask("Task3");

		scheduler.scheduleTasks();

		String[] actualOrder = scheduler.getExecutedTasks().toArray(new String[0]);

		boolean orderIsCorrect = actualOrder.length == 3; // Checks if all tasks are executed, not the order
		yakshaAssert(currentTest(), orderIsCorrect ? "true" : "false", businessTestFile);
	}

	@Test
	void testTwoTasksWithDependency() throws IOException {
		TaskScheduler scheduler = new TaskScheduler();
		scheduler.addTask("Task1");
		scheduler.addTask("Task2");
		scheduler.addDependency("Task2", "Task1");

		scheduler.scheduleTasks();

		String[] expectedOrder = { "Task2", "Task1" };
		String[] actualOrder = scheduler.getExecutedTasks().toArray(new String[0]);

		boolean orderIsCorrect = expectedOrder.length == actualOrder.length && expectedOrder[0].equals(actualOrder[0])
				&& expectedOrder[1].equals(actualOrder[1]);
		yakshaAssert(currentTest(), orderIsCorrect ? "true" : "false", businessTestFile);
	}

	@Test
	void testSingleTask() throws IOException {
		TaskScheduler scheduler = new TaskScheduler();
		scheduler.addTask("Task1");
		scheduler.scheduleTasks();

		String[] expectedOrder = { "Task1" };
		String[] actualOrder = scheduler.getExecutedTasks().toArray(new String[0]);

		boolean orderIsCorrect = expectedOrder.length == actualOrder.length && expectedOrder[0].equals(actualOrder[0]);
		yakshaAssert(currentTest(), orderIsCorrect ? "true" : "false", businessTestFile);
	}
}