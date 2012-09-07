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
package org.gedcomx.build.enunciate.rs;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.*;
import net.sf.jelly.apt.Context;
import org.gedcomx.rt.rs.*;
import org.gedcomx.rt.rs.ResourceBinding;

import javax.xml.namespace.QName;
import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author Ryan Heaton
 */
public final class ResourceLink implements Comparable<ResourceLink> {

  final String rel;
  final String description;
  final boolean template;
  final QName resource;
  final ResourceServiceProcessor processor;

  public ResourceLink(org.gedcomx.rt.rs.ResourceLink meta, ResourceServiceProcessor processor) {
    this.rel = meta.rel();
    this.description = meta.description();
    this.template = meta.template();
    this.processor = processor;

    TypeDeclaration definedBy = null;

    try {
      Class<?> clazz = meta.definedBy();
      definedBy = Context.getCurrentEnvironment().getTypeDeclaration(clazz.getName());
    }
    catch (MirroredTypeException e) {
      TypeMirror typeMirror = e.getTypeMirror();
      if (typeMirror instanceof DeclaredType && ((DeclaredType) typeMirror).getDeclaration() != null) {
        definedBy = ((DeclaredType) typeMirror).getDeclaration();
      }
    }

    this.resource = findResolvesTo(definedBy);
  }

  private QName findResolvesTo(TypeDeclaration definedBy) {
    ResourceBinding binding = findFirst(definedBy, ResourceBinding.class);
    ResourceDefinition definition = findFirst(definedBy, ResourceDefinition.class);
    if (binding != null && definition != null) {
      String namespace = binding.namespace();
      String name = binding.name();
      if ("##default".equals(namespace)) {
        namespace = definition.namespace();
      }
      if ("##default".equals(name)) {
        name = definition.name();
      }

      return new QName(namespace, name);
    }
    else if (definition != null) {
      return new QName(definition.namespace(), definition.name());
    }
    return null;
  }

  private <M extends Annotation> M findFirst(TypeDeclaration definedBy, Class<M> a) {
    M annotation = definedBy.getAnnotation(a);
    if (annotation == null) {
      Collection<InterfaceType> ifaces = definedBy.getSuperinterfaces();
      for (InterfaceType iface : ifaces) {
        annotation = findFirst(iface.getDeclaration(), a);
        if (annotation != null) {
          return annotation;
        }
      }

      if (definedBy instanceof ClassDeclaration) {
        ClassType superclass = ((ClassDeclaration) definedBy).getSuperclass();
        if (superclass != null) {
          annotation = findFirst(superclass.getDeclaration(), a);
        }
      }
    }
    return annotation;
  }

  public String getRel() {
    return rel;
  }

  public String getDescription() {
    return description;
  }

  public LinkReference getResolvesTo() {
    LinkReference resolution = this.processor.getBindingsByName().get(this.resource.getLocalPart());
    if (resolution == null) {
      resolution = this.processor.findResourceDefinition(this.resource);
    }
    return resolution;
  }

  public boolean isTemplate() {
    return template;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ResourceLink that = (ResourceLink) o;

    if (rel != null ? !rel.equals(that.rel) : that.rel != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return rel != null ? rel.hashCode() : 0;
  }

  @Override
  public int compareTo(ResourceLink o) {
    return this.rel.compareTo(o.rel);
  }
}
