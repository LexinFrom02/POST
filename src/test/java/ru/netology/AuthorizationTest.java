package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.DataGenerator;
import ru.netology.RegistrationDto;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthorizationTest {

    @BeforeEach
    void setUpConnection() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCheckLoginActiveUser() {
        RegistrationDto registrationDto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpUser(registrationDto);
        $("[name=login]").setValue(registrationDto.getLogin());
        $("[name=password]").setValue(registrationDto.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }
    @Test
    void shouldCheckLoginBlockedUser() {
        RegistrationDto registrationDto = DataGenerator.Registration.generateBlockedUser();
        DataGenerator.UserGenerator.setUpUser(registrationDto);
        $("[name=login]").setValue(registrationDto.getLogin());
        $("[name=password]").setValue(registrationDto.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldCheckLoginActiveUserInvalidLoginValidPassword() {
        RegistrationDto registrationDto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpUser(registrationDto);
        $("[name=login]").setValue("1");
        $("[name=password]").setValue(registrationDto.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Неверно указан логин")).shouldBe(Condition.visible);
    }

    @Test
    void shouldCheckLoginActiveUserValidUserInvalidPassword() {
        RegistrationDto registrationDto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpUser(registrationDto);
        $("[name=login]").setValue(registrationDto.getLogin());
        $("[name=password]").setValue("1");
        $(withText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldCheckLoginActiveUserInvalidUserInvalidPassword() {
        RegistrationDto registrationDto = DataGenerator.Registration.generateActiveUser();
        DataGenerator.UserGenerator.setUpUser(registrationDto);
        $("[name=login]").setValue("1");
        $("[name=password]").setValue("1");
        $(withText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

}
