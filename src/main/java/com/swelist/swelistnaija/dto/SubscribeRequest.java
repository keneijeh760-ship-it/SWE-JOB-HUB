package com.swelist.swelistnaija.dto;

import com.swelist.swelistnaija.domian.LocationPreference;
import com.swelist.swelistnaija.domian.RolePreference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeRequest {
    private String email;
    private List<RolePreference> rolePreferences;
    private LocationPreference locationPreference;
}
