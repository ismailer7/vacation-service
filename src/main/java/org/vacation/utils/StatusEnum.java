package org.vacation.utils;

public enum StatusEnum {
    CREATED (1, "Created"),
    PENDING (2, "Pending Approval"),
    APPROVED (3, "Approved"),
    REJECTED (4, "Rejected");

    private static StatusEnum[] statuses = StatusEnum.values();
    private int ordinal = 0;


    StatusEnum(int ordinal, String msg) {
        this.ordinal = ordinal;
    }

    public static StatusEnum getByOrdinal(int ordinal) {
        for (StatusEnum status : statuses) {
            if(status.ordinal == ordinal) {
                return status;
            }
        }
        return null;
    }

}
