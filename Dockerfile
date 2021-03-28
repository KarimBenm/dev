FROM node:12.7-alpine AS build
WORKDIR /usr/src/front
COPY package.json ./
RUN npm install
COPY . .
RUN npm build --prod --base-href=gdsFront --aot

FROM nginx:alpine
COPY --from=build /usr/src/front/dist/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf






