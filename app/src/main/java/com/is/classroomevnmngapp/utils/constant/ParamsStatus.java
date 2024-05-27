package com.is.classroomevnmngapp.utils.constant;

public interface ParamsStatus {
    /**
     * insert new record first once in local but not upload
     */
    int UPLOAD_STATUS_OFF=0;
    int UPLOAD_STATUS_ONN=1;
    /**
     * status was value 1 but
     *  update change value to 3 in local but not upload
     */
    int UPLOAD_STATUS_ONN_UPDATE=3;
    int ACTIVE_STATUS_OFF=0;
    int ACTIVE_STATUS_ONN=1;
}
