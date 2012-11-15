/**
 * Copyright 2011-2012 Intellectual Reserve, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gedcomx.rt.rs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identification of application state.
 *
 * @author Ryan Heaton
 */
@Retention ( RetentionPolicy.RUNTIME )
@Target ({ })
public @interface StateDefinition {

  /**
   * The name of the state being defined.
   *
   * @return The name of the state being defined.
   */
  String name();

  /**
   * 'rel' identifier for the state.
   *
   * @see <a href="http://tools.ietf.org/html/draft-nottingham-http-link-header-10">Web Linking</a>
   * @return The state identifier.
   */
  String rel();

  /**
   * A human-readable description of the state definition.
   *
   * @return A human-readable description of the state definition.
   */
  String description();

  /**
   * The transitions to other application states.
   *
   * @return The transitions to other application states.
   */
  StateTransition[] transitions() default {};

}
