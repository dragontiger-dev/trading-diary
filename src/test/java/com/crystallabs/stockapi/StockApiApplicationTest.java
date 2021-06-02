package com.crystallabs.stockapi;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class StockApiApplicationTest {

    @Test
    public void main() {
        StockApiApplication.main(new String[] {});
    }
}