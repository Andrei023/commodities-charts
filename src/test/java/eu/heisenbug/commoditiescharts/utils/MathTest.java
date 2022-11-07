package eu.heisenbug.commoditiescharts.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MathTest {

    @Test
    public void WHEN_invertValue_THEN_valueIsCorrectlyInverted() {
        double result = Math.invertValue(4.87902220492E-5);
        Assertions.assertEquals(20495.91, result);
    }
}
