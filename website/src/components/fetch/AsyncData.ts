export type AsyncData<D, Q = any> = {
    loading?: boolean;
    query?: Q;
    data?: D;
    error?: any;
}

export function asyncLoadData<Q, D>(query: Q, doLoad: (query: Q) => Promise<D>, update: (d: Readonly<AsyncData<D, Q>>) => void, errorHandler: (e: Error) => void = e => console.error(e)) {
    update({loading: true, query: query});
    doLoad(query)
        .then(data => update({query: query, data}))
        .catch(error => {
            update({query: query, error: error.message || "error"});
            if (errorHandler) errorHandler(error);
        });
}