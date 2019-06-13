import library.entities.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(JUnit4.class)
public class EntitiesTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void loginIsNull(){
        User user = new User(1,null, "start123", "Jan", "Kowalski", 21, 11111111,
                123456789, "READER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assert.assertEquals( 1, constraintViolations.size() );
        Assert.assertEquals(
                "required",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void passwordHasLowerLengthThanFive(){
        User user = new User(1,"janek", "1234", "Jan", "Kowalski", 21, 11111111,
                123456789, "READER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assert.assertEquals( 1, constraintViolations.size() );
        Assert.assertEquals(
                "size must be between 5 and 255",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void loginIsEmpty(){
        User user = new User(1,"", "start123", "Jan", "Kowalski", 21, 11111111,
                123456789, "READER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assert.assertEquals( 1, constraintViolations.size() );
        Assert.assertEquals(
                "size must be between 2 and 40",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void passwordIsEmpty(){
        User user = new User(1,"janek", "", "Jan", "Kowalski", 21, 11111111,
                123456789, "READER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assert.assertEquals( 1, constraintViolations.size() );
        Assert.assertEquals(
                "size must be between 5 and 255",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void passwordIsCorrect(){
        User user = new User(1,"janek", "start123", "Jan", "Kowalski", 21, 11111111,
                123456789, "READER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assert.assertEquals( 0, constraintViolations.size() );
    }

    @Test
    public void ageIsUnderZero(){
        User user = new User(1,"janek", "start123", "Jan", "Kowalski", -21, 11111111,
                123456789, "READER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assert.assertEquals( 0, constraintViolations.size() );
    }

}
