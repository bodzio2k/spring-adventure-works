package com.ipodolak.adventureworks.controller.production;

import com.ipodolak.adventureworks.dto.production.ProductDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {

    @Override
    public EntityModel<ProductDto> toModel(ProductDto product) {
        EntityModel<ProductDto> model = EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        if (product.getColor() != null) {
            model.add(linkTo(methodOn(ProductController.class).getByColor(product.getColor())).withRel("products-by-color"));
        }

        return model;
    }
}
