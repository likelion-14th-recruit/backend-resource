package org.likelion.recruit.resource.executiveMember.domain;

public enum Position {
    PRESIDENT(1),
    VICE_PRESIDENT(2),
    PART_LEADER(3),
    MEMBER(4);

    private final int priority;

    Position(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
