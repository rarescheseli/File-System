public class Cat extends Command {

    public Cat(String toExecute) {
        super(toExecute);
    }

    /**
     * Prints the text written in a file, if possible
     */
    @Override
    public int tryToExecute() {
        FileSystem fSystem = FileSystem.getInstance();

        String toPrint = AssistantCommands.process(toExecute);
        if (toPrint.startsWith("-")) {
            return Integer.parseInt(toPrint);
        }
        if (toPrint.equals(".") || toPrint.equals("..")) {
            return -1;
        }

        Repository target = AssistantCommands.checkForRepo(toPrint);
        if (target != null) {
            if (target.getContent() != null) {
                return -1;
            }

            if (fSystem.getCurrentUser().equals(fSystem.getRoot())) {
                System.out.println(((SimpleFile) target).getText());
                return 0;
            }

            if (AssistantCommands.checkReadingRights(target) != 0) {
                return -4;
            }
            System.out.println(((SimpleFile) target).getText());
        } else {
            return -11;
        }
        return 0;
    }
}
