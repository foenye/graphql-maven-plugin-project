#################################################################################################################
## Import of common.vm  (commons Velocity macro and definitions)
#################################################################################################################
#parse ("templates/common.vm")
##
##
package ${configuration.packageName}.implementations.${pkg};

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.reactivestreams.Publisher;

import com.graphql_java_generator.annotation.GraphQLDirective;
import com.graphql_java_generator.util.GraphqlUtils;

import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

@Component
@lombok.RequiredArgsConstructor
public class ${dataFetchersDelegate.pascalCaseName}Impl implements ${packageUtilName}.${dataFetchersDelegate.pascalCaseName} {
	
#foreach ($dataFetcher in $dataFetchersDelegate.dataFetchers)
##
##
#if(${dataFetcher.batchMapping})

#if ($configuration.batchMappingDataFetcherReturnType == "MONO_MAP")
#set($return = "reactor.core.publisher.Mono<java.util.Map<${dataFetcher.graphQLOriginType.classFullName}, ${dataFetcher.field.type.classFullName}>>")
#elseif ($configuration.batchMappingDataFetcherReturnType == "MAP")
#set($return = "java.util.Map<${dataFetcher.graphQLOriginType.classFullName}, ${dataFetcher.field.type.classFullName}>")
#elseif ($configuration.batchMappingDataFetcherReturnType == "FLUX")
#set($return = "reactor.core.publisher.Flux<${dataFetcher.field.type.classFullName}>")
#elseif ($configuration.batchMappingDataFetcherReturnType == "COLLECTION")
#set($return = "java.util.Collection<${dataFetcher.field.type.classFullName}>")
#else
#error Unexpected value for batchMappingDataFetcherReturnType: $configuration.batchMappingDataFetcherReturnType 
#end
##
	@Override
	public #evaluate($return) ${dataFetcher.field.javaName}(//
			BatchLoaderEnvironment batchLoaderEnvironment, //
			GraphQLContext graphQLContext, //
			List<${dataFetcher.graphQLOriginType.classFullName}> keys) {
		return null;
	}

#else        ## that is: ${dataFetcher.batchMapping} is false

#appliedDirectives(${dataFetcher.field.appliedDirectives}, "	")
	@Override
	public Object ${dataFetcher.javaName}(
			DataFetchingEnvironment dataFetchingEnvironment#if ($dataFetcher.withDataLoader),
			DataLoader<${dataFetcher.field.type.identifier.javaTypeFullClassname}, ${dataFetcher.field.type.classFullName}> dataLoader#end#if($dataFetcher.graphQLOriginType),
			${dataFetcher.graphQLOriginType.classFullName} origin#end#foreach($argument in $dataFetcher.field.inputParameters),
			#appliedDirectives(${argument.appliedDirectives}, "			")
			${argument.javaTypeFullClassname} ${argument.javaName}#end) {
		return null;
	}
##
##
##
##
##
##
##
##
##
##

#end   ## #if(${dataFetcher.batchMapping})
#end   ## #foreach ($dataFetcher in $dataFetchersDelegate.dataFetchers)
##
##
##
#foreach ($batchLoader in $dataFetchersDelegate.batchLoaders)

#end
}
