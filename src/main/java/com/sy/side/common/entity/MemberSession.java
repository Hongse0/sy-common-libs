package com.sy.side.common.entity;

import com.sy.side.common.auth.constants.MemberRoles;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSession {
    private long memberId;
    private String loginType;   // FRONT-OFFICE | BACK-OFFICE
    private String snsType;     // KAKAO | APPLE
    private String deviceUuid;
    private String appVersion;
    private String osType;      // ios | android
    private String osVersion;
    private List<MemberRoles> role;
}
