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
package io.github.photowey.riff.infras.model.constant;

/**
 * {@code PaginationConstants}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/07/15
 */
public interface PaginationConstants {

    // ----------------------------------------------------------------

    /**
     * The default page no.
     */
    long DEFAULT_PAGE_NO = 1L;
    /**
     * The default page size.
     */
    long DEFAULT_PAGE_SIZE = 10L;
    /**
     * The default total count.
     */
    long DEFAULT_TOTAL_COUNT = 0L;

    // ----------------------------------------------------------------

    /**
     * The min page size.
     */
    long MIN_PAGE_SIZE_THRESHOLD = 1L;
    /**
     * The max page size.
     */
    long MAX_PAGE_SIZE_THRESHOLD = 100L;
}
