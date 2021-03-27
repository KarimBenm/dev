FROM node:12.7-alpine AS build
WORKDIR /usr/src/front
COPY package.json ./
RUN npm install
COPY . .
CMD ["npm", "build"]

FROM nginx:alpine
COPY --from=build /usr/src/front/dist/GDS-front/ /usr/share/nginx/html
COPY --from=build /usr/src/front/dist/GDS-front/ /GDS-front
COPY nginx.conf /etc/nginx/nginx.conf






