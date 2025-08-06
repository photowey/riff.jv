/*
 * Copyright (c) 2025-present
 * the original author(photowey<photowey@gmail.com>) or authors All rights reserved.
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
package io.github.photowey.riff.business.job.core.constant;

/**
 * {@code MessageConstants}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/08/04
 */
public interface MessageConstants {

    interface Job {
        String ERROR_CHILD_JOB_NOT_FOUND = "The child job [{}] was not found";
    }

    interface JobChain {

        /**
         * Error message when neither child job ID nor code is provided.
         * Both are null or empty.
         */
        String ERROR_CHILD_JOB_ID_AND_CODE_NOT_PROVIDED =
            "Child job ID and code are both missing. At least one must be provided.";

        /**
         * Error message format: thrown when the parent job does not exist during job chain addition.
         * Placeholder {@code {}} represents the parent job ID.
         */
        String ERROR_PARENT_JOB_NOT_EXISTS = "Parent job [ID: {}] does not exist.";

        String ERROR_CHILD_JOB_CODE_MISMATCH = "The child code does not match the database child code: [{}]";
    }
}
