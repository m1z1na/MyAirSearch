package org.m1z1na.Searcher;

import java.util.ArrayList;
import java.util.List;

/* Класс предназначен для определения корректности выражения */
public class CheckLine {
    public static List<Word> lexAnalyze(String expText) {
        ArrayList<Word> words = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    words.add(new Word(WordType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    words.add(new Word(WordType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '<':
                    String nextChar = String.valueOf(expText.charAt(pos + 1));
                    if (nextChar.equals(">")) {
                        words.add(new Word(WordType.OP_NE, c));
                        pos++;
                    } else {
                        words.add(new Word(WordType.OP_LT, c));
                    }
                    pos++;
                    continue;
                case '>':
                    words.add(new Word(WordType.OP_GT, c));
                    pos++;
                    continue;
                case '=':
                    words.add(new Word(WordType.OP_EQ, c));
                    pos++;
                    continue;

                case '&':
                    words.add(new Word(WordType.AND, c));
                    pos++;
                    continue;
                case '|':
                    words.add(new Word(WordType.OR, c));
                    pos++;
                    pos++;
                    continue;

                default:
                    if ((c <= '9' && c >= '0') || '-' == c) {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while ((c <= '9' && c >= '0') || c == '.');
                        words.add(new Word(WordType.NUMBER, sb.toString()));
                    } else if (c == '"') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c != '"');
                        sb.append(c);
                        pos++;
                        words.add(new Word(WordType.STRING, sb.toString()));
                    } else if (c == '\\') {
                        /*техническое нет?*/
                        StringBuilder sb = new StringBuilder();
                        sb.append(c);
                        pos++;
                        pos++;
                        words.add(new Word(WordType.STRING, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: " + c);
                        }
                        pos++;
                    }
            }
        }
        words.add(new Word(WordType.EOF, ""));
        return words;
    }


    public static String expr(WordBuffer words) {
        Word lexeme = words.next();
        if (lexeme.type == WordType.EOF) {
            return "f";
        } else {
            words.back();
            return logic(words);
        }
    }

    public static String logic(WordBuffer words) {
        String value = math(words);
        while (true) {
            Word word = words.next();
            switch (word.type) {
                case OR:
//                    value = value.contains("t") || math(words).contains("t") ? "t" : "f";

                    value = value.contains("t") || logic(words).contains("t") ? "t" : "f";
                    break;
                case AND:
                    value = math(words).contains("t") && value.contains("t") ? "t" : "f";
//                    value = math(words).contains("t") && logic(words).contains("t") ? "t" : "f";
                    break;
                case OP_LT:
                    value = Integer.valueOf(value) < Integer.valueOf(math(words)) ? "t" : "f";
                    break;
                case OP_GT:
                    value = Integer.valueOf(value) > Integer.valueOf(math(words)) ? "t" : "f";
                    break;
                case EOF:
                case NUMBER:
                case RIGHT_BRACKET:
                    words.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token in logic: " + word.value
                            + " at position: " + words.getPos());
            }
        }
    }

    public static String math(WordBuffer words) {
        String value = factor(words);
        while (true) {
            Word word = words.next();
            switch (word.type) {
                case OP_EQ:
                    value = value.equals(factor(words)) ? "t" : "f";
                    break;
                case OP_NE:
                    value = !value.equals(factor(words)) ? "t" : "f";
                    break;
                case OP_LT:
                    value = Integer.valueOf(value) < Integer.valueOf(factor(words)) ? "t" : "f";
                    break;
                case OP_GT:
                    value = Integer.valueOf(value) > Integer.valueOf(factor(words)) ? "t" : "f";
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OR:
                case AND:
                case NUMBER:
                case STRING:
                    words.back();
                    return String.valueOf(value);
                default:

                    throw new RuntimeException("Unexpected token in math: " + word.value
                            + " at position: " + words.getPos());
            }
        }
    }

    public static String factor(WordBuffer words) {
        Word word = words.next();
        switch (word.type) {
            case STRING:
                return word.value;
            case NUMBER:
                return String.valueOf(word.value);
            case LEFT_BRACKET:
                String value = logic(words);
                word = words.next();
                if (word.type != WordType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token9: " + word.value
                            + " at position: " + words.getPos());
                }
                return value;

            default:
                throw new RuntimeException("Unexpected token3: " + word.value
                        + " at position: " + words.getPos());
        }
    }

    public boolean check(String expressionText) {
        List<Word> words = lexAnalyze(expressionText);
        WordBuffer wordBuffer = new WordBuffer(words);
        return expr(wordBuffer) == "t";
    }

    public enum WordType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_LT, OP_GT, OP_EQ, OP_NE,
        AND, OR,
        NUMBER,
        STRING,
        EOF
    }

    public static class Word {
        WordType type;
        String value;

        public Word(WordType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Word(WordType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

    }

    public static class WordBuffer {
        public List<Word> words;
        private int pos;

        public WordBuffer(List<Word> words) {
            this.words = words;
        }

        public Word next() {
            return words.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

}