export interface ResponseStatusException {
  cause: Cause[];
  stackTrace: StackTrace[];
  status: string;
  headers: {};
  responseHeaders: {};
  message: string;
  mostSpecificCause: Cause[];
  rootCause: {
    cause: Cause[];
    stackTrace: StackTrace[];
    message: string;
    localizedMessage: string;
    supressed: {
      stackTrace: StackTrace[];
      message: string;
      localizedMessage: string;
    }[];
  };
  localizedMessage: string;
  supressed: {
    stackTrace: StackTrace[];
    message: string;
    localizedMessage: string;
  };
}

export interface Cause {
  stackTrace: StackTrace[];
  message: string;
  localizedMessage: string;
  supressed: {
    stackTrace: StackTrace[];
    message: string;
    localizedMessage: string;
  };
}

export interface StackTrace {
  methodName: string;
  fileName: string;
  lineNumber: string;
  className: string;
  nativeMethod: boolean;
}
