export type AsyncData<D, Q = any> = {
    loading?: boolean;
    loaded?: boolean;
    query?: Q;
    data?: D;
    error?: any;
}

export function asyncLoadData<Q, D>(query: Q, doLoad: (query: Q) => Promise<D>, update: (d: Readonly<AsyncData<D, Q>>) => void, errorHandler: (e: Error) => void = e => console.error(e)) {
    update({loading: true, query: query});
    doLoad(query)
        .then(data => update({query: query, loaded: true, data}))
        .catch(error => {
            update({query: query, error: error.message || "error", loaded: true});
            if (errorHandler) errorHandler(error);
        });
}

export function mapAsyncData<Q, F, T>(async: AsyncData<F, Q>, func: (f: F) => T): AsyncData<T, Q> {
    return {...async, data: async.data ? func(async.data) : null};
}