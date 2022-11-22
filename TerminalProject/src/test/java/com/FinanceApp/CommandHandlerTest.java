package com.FinanceApp;

import com.FinanceApp.Commands.CommandHandler;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHandlerTest {
    @Test
    public void parseNullArgs_CommandCallShouldFail() {
        // Arrange
        var handler = new CommandHandler();
        String[] args = null;

        // Act
        boolean actual = handler.CallCommand(args);
        boolean expected = false;

        // Assert
        assertEquals(expected, actual);
    }
}
