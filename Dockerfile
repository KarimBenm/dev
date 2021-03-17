
FROM nginx:1.17.1-alpine
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=build C:\Users\Karim\Desktop\ProdProjects\prod21_02_21\GDS-front\dist\GDS-front \usr\share\nginx\html
EXPOSE 80

