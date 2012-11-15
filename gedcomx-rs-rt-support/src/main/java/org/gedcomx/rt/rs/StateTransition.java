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
 * Metadata used to describe the state transitions.
 *
 * @author Ryan Heaton
 */
@Retention ( RetentionPolicy.RUNTIME )
@Target ({ })
public @interface StateTransition {

  /**
   * Identifier for the state to which this is a transition. This is the value that will be used for the
   * <tt>rel</tt> attribute of the link.
   *
   * @see <a href="http://tools.ietf.org/html/draft-nottingham-http-link-header-10">Web Linking</a>
   * @return The state transition identifier.
   */
  String rel();

  /**
   * A human-readable description of the resource relationship.
   *
   * @return A human-readable description of the resource relationship.
   */
  String description();

  /**
   * The scope of the link, meaning what data type can be expected to carry the link.
   *
   * @return The scope of the link, meaning what data type can be expected to carry the link.
   */
  Class<?>[] scope() default {};

  /**
   * Whether this transition is provided as a templated link (as opposed to an outbound link).
   *
   * @return Whether this transition is provided as a templated link (as opposed to an outbound link).
   */
  boolean template() default false;
}
