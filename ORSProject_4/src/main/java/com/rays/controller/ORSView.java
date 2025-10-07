package com.rays.controller;

/**
 * ORSView interface holds all the constants for the application context paths,
 * JSP page paths, and controller paths used across the ORSProject_4
 * application.
 * <p>
 * It centralizes the path management for views and controllers to ensure
 * consistency and ease of maintenance.
 * </p>
 * 
 * @author Aastik Sahu
 */
public interface ORSView {

	/** Application context root */
	public String APP_CONTEXT = "/ORSProject_4";

	/** Folder where JSP pages are located */
	public String PAGE_FOLDER = "/jsp";

	/** Path to the generated JavaDoc index */
	public String JAVA_DOC = APP_CONTEXT + "/doc/index.html";

	/** Welcome page view path */
	public String WELCOME_VIEW = PAGE_FOLDER + "/WelcomeView.jsp";

	/** Welcome controller path */
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	/** User registration view path */
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";

	/** User registration controller path */
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

	/** Forget password view path */
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";

	/** Forget password controller path */
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	/** Login page view path */
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";

	/** Login controller path */
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

	/** My Profile view page */
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";

	/** My Profile controller */
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	/** Change Password view page */
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";

	/** Change Password controller */
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

	/** Get Marksheet view */
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";

	/** Get Marksheet controller */
	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	/** Marksheet Merit List view */
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	/** Marksheet Merit List controller */
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	/** User view */
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";

	/** User controller */
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	/** User list view */
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";

	/** User list controller */
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	/** Role view */
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";

	/** Role controller */
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	/** Role list view */
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";

	/** Role list controller */
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	/** College view */
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";

	/** College controller */
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

	/** College list view */
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";

	/** College list controller */
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	/** Student view */
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";

	/** Student controller */
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

	/** Student list view */
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";

	/** Student list controller */
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

	/** Marksheet view */
	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";

	/** Marksheet controller */
	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	/** Marksheet list view */
	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";

	/** Marksheet list controller */
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	/** Course view */
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";

	/** Course controller */
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

	/** Course list view */
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";

	/** Course list controller */
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	/** Subject view */
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";

	/** Subject controller */
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

	/** Subject list view */
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";

	/** Subject list controller */
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	/** Timetable view */
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimetableView.jsp";

	/** Timetable controller */
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimetableCtl";

	/** Timetable list view */
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimetableListView.jsp";

	/** Timetable list controller */
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimetableListCtl";

	/** Faculty view */
	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";

	/** Faculty controller */
	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

	/** Faculty list view */
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";

	/** Faculty list controller */
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	
	/** URL path to the patient view JSP page */
	public String PATIENT_VIEW = PAGE_FOLDER + "/PatientView.jsp";

	/** Controller path for handling patient-related requests. */
	public String PATIENT_CTL = APP_CONTEXT + "/ctl/PatientCtl";

	/** URL path to the patient list view JSP page. */
	public String PATIENT_LIST_VIEW = PAGE_FOLDER + "/PatientListView.jsp";

	/** Controller path for handling patient list-related requests. */
	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientListCtl";

	/** URL path to the doctor view JSP page. */
	public String DOCTOR_VIEW = PAGE_FOLDER + "/DoctorView.jsp";

	/** Controller path for handling doctor-related requests. */
	public String DOCTOR_CTL = APP_CONTEXT + "/ctl/DoctorCtl";

	/** URL path to the doctor list view JSP page. */
	public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/DoctorListView.jsp";

	/** Controller path for handling doctor list-related requests. */
	public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/DoctorListCtl";

	/** URL path to the follow-up view JSP page. */
	public String FOLLOW_UP_VIEW = PAGE_FOLDER + "/FollowUpView.jsp";

	/** Controller path for handling follow-up-related requests. */
	public String FOLLOW_UP_CTL = APP_CONTEXT + "/ctl/FollowUpCtl";

	/** URL path to the follow-up list view JSP page. */
	public String FOLLOW_UP_LIST_VIEW = PAGE_FOLDER + "/FollowUpListView.jsp";

	/** Controller path for handling follow-up list-related requests. */
	public String FOLLOW_UP_LIST_CTL = APP_CONTEXT + "/ctl/FollowUpListCtl";

	/** Error view page */
	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView.jsp";

	/** Error controller */
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

}
