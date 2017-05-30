package com.github.pgutkowski.kgraphql.schema.execution

import com.github.pgutkowski.kgraphql.request.Arguments
import com.github.pgutkowski.kgraphql.request.Variables
import com.github.pgutkowski.kgraphql.schema.model.FunctionWrapper

/**
 * 
 */
internal class FunctionInvoker(private val argumentsHandler: ArgumentsHandler) {

    //TODO: Refractor to avoid using so many arguments
    fun <T>invokeFunWrapper(funName: String,
                            functionWrapper: FunctionWrapper<T>,
                            receiver: Any?,
                            args: Arguments?,
                            variables: Variables): T? {
        if(functionWrapper.arity() != (args?.size ?: 0) + (if(receiver!= null) 1 else 0) ){

            throw com.github.pgutkowski.kgraphql.SyntaxException(
                    "$funName does support arguments: ${functionWrapper.kFunction.parameters.map { it.name }}. found arguments: ${args?.keys}"
            )
        }

        val transformedArgs = argumentsHandler.transformArguments(functionWrapper, args, variables)

        return if(receiver != null){
            functionWrapper.invoke(receiver, *transformedArgs.toTypedArray())
        } else {
            functionWrapper.invoke(*transformedArgs.toTypedArray())
        }
    }
}