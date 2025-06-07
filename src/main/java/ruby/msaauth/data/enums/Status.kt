package ruby.msaauth.data.enums

enum class Status {
    REQUEST,        // 가입 요청
    USED,           // 승인 후 사용 상태
    UNUSED,         // 미사용 상태
    DELETE          // 삭제(사용자 요청 후 삭제 상태. 해당 상태에서 일정 기간 후 기록 완전 삭제)
}
