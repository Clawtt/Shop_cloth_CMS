package com.shop_Karol_Herzog_Banasik;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LocalDateTimeProviderTest {

    @Test
    public void shouldGetCurrentTime() {
        //given
        LocalDateTimeProvider localDateTimeProvider = new LocalDateTimeProvider();
        //when
        LocalDateTime localDateTime = localDateTimeProvider.currentTime();
        //then
        Assertions.assertThat(localDateTime).isNotNull();
    }

}
