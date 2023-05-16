package org.m1z1na.Searcher;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CheckLineTest {

    private final CheckLine checkLine = new CheckLine();

    @Test
    public void technicalTest() {

        assertThat(checkLine.check("100>10&\"GKA\"=\"GKA\"||5>6")).isEqualTo(true);
        assertThat(checkLine.check("2>10&\"GKA\"=\"GKA\"||5>6")).isEqualTo(false);
    }
    @Test
    public void equalsIsCorrect() {

        assertThat(checkLine.check("1=1")).isEqualTo(true);
        assertThat(checkLine.check("157=157")).isEqualTo(true);

    }

    @Test
    public void notEqualsIsCorrect() {

        assertThat(checkLine.check("1=2")).isEqualTo(false);

    }

    @Test
    public void GreaterThan() {

        assertThat(checkLine.check("20>15")).isEqualTo(true);

    }

    @Test
    public void notGreaterThan() {

        assertThat(checkLine.check("2>2")).isEqualTo(false);

    }

    @Test
    public void LessThan() {

        assertThat(checkLine.check("2<15")).isEqualTo(true);

    }

    @Test
    public void notLessThan() {

        assertThat(checkLine.check("9<2")).isEqualTo(false);

    }

    @Test
    public void equalsWords() {

        assertThat(checkLine.check("\"abc\"=\"abc\"")).isEqualTo(true);

    }

    @Test
    public void notEqualsWords() {

        assertThat(checkLine.check("\"abc\"=\"abcd\"")).isEqualTo(false);
        assertThat(checkLine.check("\"abc\"<>\"abcd\"")).isEqualTo(true);

    }

    @Test
    public void andIsTrue() {

        assertThat(checkLine.check("\"abc\"=\"abc\"&1=1")).isEqualTo(true);

    }

    @Test
    public void andIsFalse() {

        assertThat(checkLine.check("\"abc\"=\"abc\"&1=2")).isEqualTo(false);
        assertThat(checkLine.check("\"abc\"=\"abcd\"&1=1")).isEqualTo(false);

    }

    @Test
    public void orIsTrue() {

        assertThat(checkLine.check("1=2||\"abc\"=\"abc\"")).isEqualTo(true);

    }

    @Test
    public void orIsFalse() {

        assertThat(checkLine.check("\"abc\"=\"abcd\"||1=2")).isEqualTo(false);

    }

    @Test
    public void complexExpressionIsTrue() {

        assertThat(checkLine.check("(1=1)&(2=2)")).isEqualTo(true);
        assertThat(checkLine.check("(1=1&2>1)&(2=3||3=3)")).isEqualTo(true);

    }

    @Test
    public void complexExpressionIsFalse() {

        assertThat(checkLine.check("(1=1)&(2=3)")).isEqualTo(false);
        assertThat(checkLine.check("(1=1&2>1)&(2=3)")).isEqualTo(false);
        assertThat(checkLine.check("(1=2||2>1)&(2=3)")).isEqualTo(false);

    }

    @Test
    public void notFail() {
        assertThat(checkLine.check("(1=1)||2>1&(9=3)")).isEqualTo(true);
        assertThat(checkLine.check("(1=1)||2>1&9=3")).isEqualTo(true);
        assertThat(checkLine.check("(1=2)||2>1&9=3")).isEqualTo(false);
        assertThat(checkLine.check("(1=5)||2>1&9=9")).isEqualTo(true);
        assertThat(checkLine.check("(1=5&1=1)||2>1&9=9")).isEqualTo(true);
        assertThat(checkLine.check("(1=5||1=1)||2>1&9=9")).isEqualTo(true);

    }
//    public void myFail() {

//        assertThat(checkLine.check("\"abc\"=\"abc\"||1=1")).isEqualTo(true);
//        assertThat(checkLine.check("(1=1||2>1)&(2=3||3=3)")).isEqualTo(true);
//         assertThat(checkLine.check("1=1||(2>1&2=3)")).isEqualTo(true);
//        assertThat(checkLine.check("(1=5||1=1)||(2>1&9=9)")).isEqualTo(true);
//         assertThat(checkLine.check("(1=1)||2>1&(9=3)")).isEqualTo(true);
//    }
}