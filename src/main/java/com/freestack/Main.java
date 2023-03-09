package com.freestack;

public class Main {

	public static void main(String[] args) {

		//Q1
		UberUser uberUser = new UberUser("Joe", "Bah");
		UberApi.enrollUser(uberUser);

		UberUser uberUser2 = new UberUser("Joe", "Bee");
		UberApi.enrollUser(uberUser2);

		UberDriver uberDriver = new UberDriver("Jane", "Dee");
		UberApi.enrollDriver(uberDriver);
		//Q2
		Booking booking1 = UberApi.bookOneDriver(uberUser);
		if (booking1 == null) throw new AssertionError("uberDriver should be found available");

		//Q3
		Booking booking2 = UberApi.bookOneDriver(uberUser2);
		if (booking2 != null) throw new AssertionError("the only one driver is already booked, " +
		                                               "we should not found any uberDriver free for now");

		UberApi.finishBooking(booking1);
		int evaluationOfTheUser = 5;
		UberApi.evaluateDriver(booking1, evaluationOfTheUser);

		//Q4
		List<Booking> bookings = UberApi.listDriverBookings(uberDriver);
		if (bookings.size() != 1) throw new AssertionError();
		if (!bookings.get(0).getScore().equals(evaluationOfTheUser)) throw new AssertionError();

		Booking booking3 = UberApi.bookOneDriver(uberUser2);
		if (booking3 == null) throw new AssertionError("uberDriver should be now available");

		//Q5
		float meanScore = UberApi.meanScore(uberDriver);
		if (meanScore != 5) throw new AssertionError("one eval of 5 should give a mean of 5");

		//Q6
		List<Booking> unfinishedBookings = UberApi.listUnfinishedBookings();
		if (unfinishedBookings.size() != 1) throw new AssertionError("only booking3 should be unfinished");

		//Q7
		List<?> scoreAndUsers = UberApi.listScoreAndUser(); //? Est à définir
		// implementer en question 5 ce qui permettra d’afficher dans la console un format du type
		// en se basant sur scoreAndUsers
		//Joe Bah a donné en moyenne une note de 5
		//Joe Bee n'a pas donné d'évaluation
		//Jane Dee n'a pas donné d'évaluation
	}

}
