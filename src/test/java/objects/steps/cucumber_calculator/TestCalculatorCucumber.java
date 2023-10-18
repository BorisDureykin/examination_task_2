package objects.steps.cucumber_calculator;

import iFellow.Calculator;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;

import static org.junit.Assert.assertEquals;

public class TestCalculatorCucumber {

    private double num1;
    private double num2;
    private char action;
    private double result;


    @Step("Дано два числа")
    @Дано("Два числа {double} {double}")
    public void twoNumbers(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    @Step("Тогда выполняем действие")
    @Когда("Выполняем действие {string}")
    public void performingAction(String action) {
        if (action.length() == 1) {
            this.action = action.charAt(0);
        } else {
            throw new IllegalArgumentException("Действие должно быть одним символом.");
        }
    }

    @Step("Тогда результат равен")
    @Тогда("Результат равен {double}")
    public void resultIsEqual(double res1) {
        switch (action) {
            case '+':
                result = Calculator.calculate(num1, num2, '+');
                break;
            case '-':
                result = Calculator.calculate(num1, num2, '-');
                break;
            case '*':
                result = Calculator.calculate(num1, num2, '*');
                break;
            case '/':
                result = Calculator.calculate(num1, num2, '/');
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемое действие: " + action);
        }
        assertEquals(res1, result, 0.001);
    }

    @Step("Сообщение о делении на ноль")
    @То("Сообщение о делении на ноль {string}")
    public void divisionByZero(String expectedMessage) {
        try {
            result = Calculator.calculate(num1, num2, '/');
        } catch (ArithmeticException e) {
            assertEquals(expectedMessage, e.getMessage());
            return;
        }
        throw new AssertionError("Ожидалось исключение ArithmeticException, но оно не было вызвано.");
    }
}
