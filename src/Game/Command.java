package Game;

public class Command {
    private String firstWord;
    private String secondWord;

    public Command(String firstWord, String secondWord) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord() {
        return firstWord;
    }

    public void setCommandWord(String commandWord) {
        this.firstWord = firstWord;
    }
    public boolean isUnknown() {
        return (firstWord == null);
    }
    public String getSecondWord() {return secondWord; }

    public boolean hasSecondWord() {
        return (secondWord != null);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Command{");
        sb.append("firstWord='").append(firstWord).append('\'');
        sb.append(", secondWord='").append(secondWord).append('\'');
        sb.append('}');
        return sb.toString();
    }
}