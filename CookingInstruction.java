public class CookingInstruction {
    private int sequence;
    private String step;

    public CookingInstruction(int sequence, String step) {
        if (sequence <= 0) {
            throw new IllegalArgumentException("Sequence must be positive");
        }
        if (step == null || step.trim().isEmpty()) {
            throw new IllegalArgumentException("Step cannot be null or empty");
        }
        this.sequence = sequence;
        this.step = step.trim();
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        if (sequence <= 0) {
            throw new IllegalArgumentException("Sequence must be positive");
        }
        this.sequence = sequence;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        if (step == null || step.trim().isEmpty()) {
            throw new IllegalArgumentException("Step cannot be null or empty");
        }
        this.step = step.trim();
    }

    @Override
    public String toString() {
        return "Step " + sequence + ": " + step;
    }
}