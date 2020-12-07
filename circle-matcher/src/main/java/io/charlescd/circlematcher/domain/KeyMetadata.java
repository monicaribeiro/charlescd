/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.charlescd.circlematcher.domain;

import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class KeyMetadata {

    private String reference;

    private String key;

    private SegmentationType type;

    private String name;

    private String circleId;

    private String workspaceId;

    private Boolean isDefault;

    private Integer percentage;

    private LocalDateTime createdAt;

    public KeyMetadata() {
    }

    public KeyMetadata(String key, Segmentation segmentation) {
        this.reference = segmentation.getReference();
        this.key = key;
        this.type = segmentation.getType();
        this.circleId = segmentation.getCircleId();
        this.name = segmentation.getName();
        this.workspaceId = segmentation.getWorkspaceId();
        this.isDefault = segmentation.getIsDefault();
        this.percentage = segmentation.getPercentage();
        this.createdAt = segmentation.getCreatedAt();
    }

    public String getReference() {
        return reference;
    }

    public String getKey() {
        return key;
    }

    public SegmentationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getCircleId() {
        return circleId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public boolean isPercentage() {
        return type.equals(SegmentationType.PERCENTAGE);
    }

    public Integer getPercentage() {
        return this.percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public int sumPercentage(Integer percentageToSum) {
        return this.percentage + percentageToSum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public KeyMetadata copy(int percentage) {
        var segmentation = new Segmentation(
                this.name, null, this.reference, this.circleId, this.type, this.workspaceId, this.isDefault,
                percentage, this.getCreatedAt()
        );
        return new KeyMetadata(this.key, segmentation);
    }
}
