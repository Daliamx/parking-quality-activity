package mx.tecmilenio.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingFeeCalculatorTest {

    @Test
    void fifteenMinutesShouldBeFree() {
        // Arrange
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        // Act
        int result = calculator.calculateFee(15, false);

        // Assert
        assertEquals(0, result);
    }

    @Test
    void sixtyOneMinutesShouldChargeOneStartedAdditionalHour() {
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        int result = calculator.calculateFee(61, false);

        assertEquals(35, result);
    }

    @Test
    void normalFeeShouldNotBeGreaterThanEighty() {
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        int result = calculator.calculateFee(600, false);

        assertEquals(80, result);
    }

    @Test
    void lostTicketShouldCostOneHundredFifty() {
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        int result = calculator.calculateFee(10, true);

        assertEquals(150, result);
    }

    @Test
    void negativeMinutesShouldBeRejected() {
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateFee(-1, false)
        );
    }

    // --- Pruebas nuevas agregadas para la Actividad 4 ---

    @Test
    void sixteenMinutesShouldCostTwenty() {
        // Arrange
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        // Act
        int result = calculator.calculateFee(16, false);

        // Assert
        assertEquals(20, result);
    }

    @Test
    void sixtyMinutesShouldStillCostTwenty() {
        // Arrange
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        // Act
        int result = calculator.calculateFee(60, false);

        // Assert
        assertEquals(20, result);
    }

    @Test
    void oneHundredTwentyOneMinutesShouldChargeTwoAdditionalHours() {
        // Arrange
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        // Act
        int result = calculator.calculateFee(121, false);

        // Assert
        assertEquals(50, result);
    }

    @Test
    void threeHundredOneMinutesShouldBeCappedAtEightyInsteadOfNinetyFive() {
        // Arrange
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        // Act
        int result = calculator.calculateFee(301, false);

        // Assert
        assertEquals(80, result);
    }

    @Test
    void lostTicketWithNegativeMinutesShouldStillChargeOneHundredFifty() {
        // Arrange
        ParkingFeeCalculator calculator = new ParkingFeeCalculator();

        // Act
        int result = calculator.calculateFee(-5, true);

        // Assert
        assertEquals(150, result);
    }
}